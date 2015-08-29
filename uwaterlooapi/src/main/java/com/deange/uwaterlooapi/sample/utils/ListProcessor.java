package com.deange.uwaterlooapi.sample.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ListProcessor<T> {

    private final List<T> mList;

    private ListProcessor(final List<T> list) {
        mList = (list != null) ? list : new ArrayList<T>();
    }

    public static <T> ListProcessor<T> on(final Collection<T> list) {
        return new ListProcessor<>(new ArrayList<>(list));
    }

    @SafeVarargs
    public static <T> ListProcessor<T> on(final T... list) {
        return new ListProcessor<>(new ArrayList<>(Arrays.asList(list)));
    }

    public <V> ListProcessor<V> map(final Mapper<T, V> mapper) {
        final List<V> newList = new ArrayList<>();
        for (final T t : mList) {
            newList.add(mapper.map(t));
        }
        return ListProcessor.on(newList);
    }

    public <V> ListProcessor<V> mapParallel(final Class<V> clazz, final Mapper<T, V> mapper) {

        final V[] newList = (V[]) Array.newInstance(clazz, mList.size());

        doParallel(new ParallelCallback<T>() {
            @Override
            public void process(final int position) {
                newList[position] = mapper.map(mList.get(position));
            }
        });

        return ListProcessor.on(newList);
    }

    public ListProcessor<T> doSerial(final Callback<T> callback) {
        for (final T t : mList) {
            callback.process(t);
        }
        return this;
    }

    public ListProcessor<T> doParallel(final Callback<T> callback) {
        doParallel(new ParallelCallback<T>() {
            @Override
            public void process(final int position) {
                callback.process(mList.get(position));
            }
        });
        return this;
    }

    public ListProcessor<T> filter(final Filter<T> filter) {
        for (final Iterator<T> iterator = mList.iterator(); iterator.hasNext(); ) {
            final T t = iterator.next();
            if (!filter.keep(t)) {
                iterator.remove();
            }
        }
        return this;
    }

    public ListProcessor<T> slices(final int size, final ListSliceProcessor<T> callback) {

        if (size <= 0) {
            throw new IllegalArgumentException("size must be > 0");
        }

        for (int i = 0; i < mList.size(); i += size) {
            final int end = Math.min(i + size, mList.size());
            final List<T> smallList = mList.subList(i, end);
            callback.process(smallList);
        }

        return this;
    }

    public String join(final char delimiter) {
        return join(String.valueOf(delimiter));
    }

    public String join(final String delimiter) {
        if (mList.isEmpty()) {
            return "";

        } else if (mList.size() == 1) {
            return String.valueOf(mList.get(0));

        } else {
            final StringBuilder sb = new StringBuilder(String.valueOf(mList.get(0)));
            for (int i = 1; i < mList.size(); i++) {
                sb.append(delimiter);
                sb.append(mList.get(i));
            }

            return sb.toString();
        }
    }

    private ListProcessor<T> doParallel(final ParallelCallback<T> callback) {

        final int threadCount = Runtime.getRuntime().availableProcessors() + 1;
        final ExecutorService service = Executors.newFixedThreadPool(threadCount);
        final int size = mList.size();
        final Semaphore semaphore = new Semaphore(1 - size);

        for (int i = 0; i < size; i++) {
            final int pos = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        callback.process(pos);
                    } finally {
                        semaphore.release();
                    }
                }
            });
        }

        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    public <V extends T> Set<V> asSet() {
        return new LinkedHashSet<>((Collection<V>) mList);
    }

    @SuppressWarnings("unchecked")
    public <V extends T> List<V> asList() {
        return new ArrayList<>((Collection<V>) mList);
    }

    public interface ListSliceProcessor<T> {
        void process(final List<T> list);
    }

    public interface Mapper<T, V> {
        V map(final T entity);
    }

    public interface Filter<T> {
        boolean keep(final T entity);
    }

    public interface Callback<T> {
        void process(final T entity);
    }

    private interface ParallelCallback<T> {
        void process(final int position);
    }

}
