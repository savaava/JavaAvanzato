package annotation.processor;

import annotation.AtMostThree;
import annotation.FieldNumberConstraint;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({"annotation.AtMostThree","annotation.FieldNumberConstraint"})
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class MyProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Il processore è stato avviato correttamente");

        Set<Class<? extends Annotation>> classAnnotationSet = new HashSet<>();
        classAnnotationSet.add(AtMostThree.class);
        classAnnotationSet.add(FieldNumberConstraint.class);
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWithAny(classAnnotationSet);

        Set<? extends Element> atMostThreeClasses = roundEnv.getElementsAnnotatedWith(AtMostThree.class);
        for (Element element : atMostThreeClasses) {
            if (element.getKind() == ElementKind.CLASS) {
                checkAtMostThree((TypeElement)element);
            }
        }

        Set<? extends Element> fieldNumberClasses = roundEnv.getElementsAnnotatedWith(FieldNumberConstraint.class);
        for (Element element : fieldNumberClasses) {
            if (element.getKind() == ElementKind.CLASS) {
                checkFieldNumberConstraint((TypeElement) element);
            }
        }

        return false;
    }

    private void checkAtMostThree(TypeElement typeElement){
        int methodCount = typeElement.getEnclosedElements()
                .stream()
                .filter(e -> e.getKind() == ElementKind.METHOD)
                .toList()
                .size();

        if(methodCount > 3) {
            printError(typeElement,
                    "La classe "+typeElement.getSimpleName()+" deve contenere al massimo 3 metodi!");
        }
    }

    private void checkFieldNumberConstraint(TypeElement typeElement) {
        FieldNumberConstraint annotation = typeElement.getAnnotation(FieldNumberConstraint.class);

        int expectedFieldCount = annotation.value();

        int fieldCount = typeElement.getEnclosedElements()
                .stream()
                .filter(e -> e.getKind() == ElementKind.FIELD)
                .toList()
                .size();

        if (fieldCount != expectedFieldCount) {
            printError(typeElement,
                    "Il numero di campi della classe "+typeElement.getSimpleName()+" è diverso dal valore dell'annotazione!");
        }
    }

    private void printError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }

    private void printWarning(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.WARNING, message, element);
    }
}