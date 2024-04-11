package com.healthcare.models

class User {
    var name: String? = null
        private set
    var email: String? = null
        private set

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(name: String?, email: String?) {
        this.name = name
        this.email = email
    }
}