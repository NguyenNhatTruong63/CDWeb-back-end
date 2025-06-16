package com.example.CDWeb.model;

public  class EmailResponse {
        private String message;
        private String email;

        public EmailResponse(String message, String email) {
            this.message = message;
            this.email = email;
        }

        public String getMessage() {
            return message;
        }

        public String getEmail() {
            return email;
        }
    }