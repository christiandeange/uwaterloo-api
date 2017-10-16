package com.deange.uwaterlooapi.sample.ui.modules.courses.views;

import android.content.Context;
import android.widget.ListView;

import com.deange.uwaterlooapi.model.courses.ExamInfo;
import com.deange.uwaterlooapi.model.courses.ExamSection;
import com.deange.uwaterlooapi.sample.R;
import com.deange.uwaterlooapi.sample.model.CombinedCourseInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class ExamInfoView extends BaseCourseView {

  @BindView(R.id.exam_list_view) ListView mListView;

  public ExamInfoView(final Context context) {
    super(context);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.view_exam_info;
  }

  @Override
  public void bind(final CombinedCourseInfo info) {
    final ExamInfo examInfo = info.getExams();

    final List<ExamSection> sections = new ArrayList<>();
    if (examInfo.getSections() != null) {
      sections.addAll(examInfo.getSections());
    }
    Collections.sort(sections,
                     (lhs, rhs) -> lhs.getSection().compareToIgnoreCase(rhs.getSection()));

    mListView.setAdapter(new ExamAdapter(getContext(), sections));
  }
}
