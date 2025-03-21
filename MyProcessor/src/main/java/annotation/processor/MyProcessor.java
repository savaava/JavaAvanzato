package annotation.processor;

import annotation.AtMostThree;
import annotation.FieldNumberConstraint;

import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic; /* per produrre messaggi di compilazione */

@SupportedAnnotationTypes({"annotation.AtMostThree","annotation.FieldNumberConstraint"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> atMostThreeClasses = roundEnv.getElementsAnnotatedWith(AtMostThree.class);
        for (Element element : atMostThreeClasses) {
            if (element.getKind() == ElementKind.CLASS) {
                checkAtMostThree(element);
            }
        }

        Set<? extends Element> fieldNumberClasses = roundEnv.getElementsAnnotatedWith(FieldNumberConstraint.class);
        for (Element element : fieldNumberClasses) {
            if (element.getKind() == ElementKind.CLASS) {
                checkFieldNumberConstraint(element);
            }
        }

        return false;
    }

    private void checkAtMostThree(Element classElement){
        Class<?> c = classElement.getClass();

        if(c.getDeclaredMethods().length > 3){
            printError(classElement, "La classe deve contenere al massimo 3 metodi !");
        }
    }
    private void checkFieldNumberConstraint(Element classElement){
        Class<?> c = classElement.getClass();

        FieldNumberConstraint a = c.getAnnotation(FieldNumberConstraint.class);
        int annotationValue = a.value();
        if(c.getDeclaredFields().length != annotationValue)
            printError(classElement,"Number of field is different from provided annotation value!");
    }

    private void printError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}