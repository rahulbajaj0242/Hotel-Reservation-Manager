module Project {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	
	exports application.controllers to javafx.fxml;
	opens application.controllers to javafx.fxml;
	
	opens application.models to javafx.base;
}
