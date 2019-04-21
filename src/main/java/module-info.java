module kr.knu.busreservations {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.mongodb.driver.core;

    opens kr.knu.busreservations to javafx.fxml;
    exports kr.knu.busreservations;
}