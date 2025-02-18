module mapA8.interpreter.interpreter {
    requires javafx.controls;
    requires javafx.fxml;

    exports map.interpreter.interpreter;
    exports map.interpreter.interpreter.services;
    exports map.interpreter.interpreter.domain.statements;

    opens map.interpreter.interpreter.services to javafx.fxml;
}
