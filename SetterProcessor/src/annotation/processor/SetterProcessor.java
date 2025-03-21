package annotation.processor;

import annotation.Setter;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic; /* per produrre messaggi di compilazione */

@SupportedAnnotationTypes({"annotation.Setter"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SetterProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        /* a quale round sto gestendo l'annotazione, il compilatore trova il codice compilato
         * con Setter: */
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(Setter.class);
        for (Element element : annotatedElements) {
            if (element.getKind() == ElementKind.METHOD) {
                checkMethod((ExecutableElement) element);
            }
        }
        return false;
    }

    private void checkMethod(ExecutableElement method) {
        String name = method.getSimpleName().toString();
        if (!name.startsWith("set")) {
            printError(method, "setter name must start with \"set\"");
        } else if (name.length() == 3) {
            printError(method, "the method name must contain more than just \"set\"");
        } else if (Character.isLowerCase(name.charAt(3))) {
            if (method.getParameters().size() == 1) {
                printError(method, "character following \"set\" must be upper case");
            }
        }

        if(method.getParameters().size() !=1) {
            printWarning(method,"No parameter to set field or too many arguments.");
        }
        if (!method.getModifiers().contains(Modifier.PUBLIC)) {
            printError(method, "setter must be public");
        }
        if (method.getModifiers().contains(Modifier.STATIC)) {
            printError(method, "setter must not be static");
        }
    }

    private void printError(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
    private void printWarning(Element element, String message) {
        messager.printMessage(Diagnostic.Kind.WARNING, message, element);
    }
}