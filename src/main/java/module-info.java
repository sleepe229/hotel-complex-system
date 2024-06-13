module com.example.cringecoding {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.cringecoding to javafx.fxml;
    exports com.example.cringecoding;
}