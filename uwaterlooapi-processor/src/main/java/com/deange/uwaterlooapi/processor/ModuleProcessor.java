package com.deange.uwaterlooapi.processor;

import com.deange.uwaterlooapi.annotations.ModuleFragment;
import com.deange.uwaterlooapi.annotations.ModuleInfo;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class ModuleProcessor extends AbstractProcessor {

  @Override
  public boolean process(
      final Set<? extends TypeElement> annotations,
      final RoundEnvironment roundEnv) {

    if (roundEnv.processingOver()) {
      return false;
    }

    final ClassName map = ClassName.get(Map.class);
    final TypeName string = TypeName.get(String.class);
    final TypeName moduleInfo = TypeName.get(ModuleInfo.class);

    final TypeName mapOfStringToModules = ParameterizedTypeName.get(map, string, moduleInfo);

    final FieldSpec sEndpoints = FieldSpec.builder(mapOfStringToModules, "sEndpoints")
                                          .addModifiers(Modifier.PRIVATE, Modifier.STATIC,
                                                        Modifier.FINAL)
                                          .initializer("new $T<>()", ClassName.get(HashMap.class))
                                          .build();

    final MethodSpec getFragmentInfo = MethodSpec.methodBuilder("getFragmentInfo")
                                                 .returns(moduleInfo)
                                                 .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                                                 .addParameter(String.class, "endpoint",
                                                               Modifier.FINAL)
                                                 .addStatement("String path = endpoint")
                                                 .addStatement(
                                                     "path = path.replace(\".json\", \"\")")
                                                 .addStatement(
                                                     "path = path.replaceAll(\"\\\\{[^\\\\}]*\\\\}\", \"*\")")
                                                 .addStatement("return sEndpoints.get(path)")
                                                 .build();

    final CodeBlock.Builder staticz = CodeBlock.builder();

    for (final Element e : roundEnv.getElementsAnnotatedWith(ModuleFragment.class)) {
      final TypeElement element = (TypeElement) e;
      final ModuleFragment moduleFragment = element.getAnnotation(ModuleFragment.class);

      staticz.addStatement("sEndpoints.put($S,\n" +
                               "new $T($T.class, " + moduleFragment.layout() + "))"
          , moduleFragment.path(), ModuleInfo.class, element.asType());
      staticz.add("\n");
    }

    final TypeSpec moduleMapClass = TypeSpec.classBuilder("ModuleMap")
                                            .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                            .addField(sEndpoints)
                                            .addStaticBlock(staticz.build())
                                            .addMethod(getFragmentInfo)
                                            .build();

    final JavaFile javaFile = JavaFile
        .builder("com.deange.uwaterlooapi.annotations", moduleMapClass)
        .build();

    try {
      javaFile.writeTo(processingEnv.getFiler());
    } catch (final IOException ignored) {
    }

    return true;
  }

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return new HashSet<>(Collections.singletonList(ModuleFragment.class.getName()));
  }

}
