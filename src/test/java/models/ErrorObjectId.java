package models;

import lombok.Getter;
@Getter
public class ErrorObjectId {
        private String timestamp;
        private long status;
        private String error;
        private String path;
        private String message;
}

