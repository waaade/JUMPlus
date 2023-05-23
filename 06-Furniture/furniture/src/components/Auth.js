import React, { useState } from 'react';
import Login from './Login.js';
import Register from './Register.js';

// Credit to https://supertokens.com/blog/building-a-login-screen-with-react-and-bootstrap for help

const Auth = () => {
    const [authMode, setAuthMode] = useState("signin");

    const toggleAuthMode = () => {
        setAuthMode(authMode === "signin" ? "signup" : "signin");
    }
    if (authMode === "signin") {
        return (
            <div>
                <Login />
                <div className="text-center">
                    Don't have an account?{" "}
                    <span className="link-primary" onClick={toggleAuthMode}>
                        Register
                    </span>
                </div>

            </div>
        )
    } else {
        return (
            <div>
                <Register />
                <div className="text-center">
                    Already have an account?{" "}
                    <span className="link-primary" onClick={toggleAuthMode}>
                        Login
                    </span>
                </div>
            </div>

        )
    }

}

export default Auth;