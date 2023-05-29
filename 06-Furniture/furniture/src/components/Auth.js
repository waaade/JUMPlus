import React, { useEffect, useState } from 'react';
import Login from './Login.js';
import Register from './Register.js';

// Credit to https://supertokens.com/blog/building-a-login-screen-with-react-and-bootstrap for help

const Auth = () => {
    const [authMode, setAuthMode] = useState("signin");
    const [loggedIn, setLoggedIn] = useState(false);
    
    const toggleAuthMode = () => {
        setAuthMode(authMode === "signin" ? "signup" : "signin");
    }

    useEffect(() => {
        if (sessionStorage.getItem("userId") > 0) {
            setLoggedIn(true);
        }
    }, [])

    if (!loggedIn) {
        if (authMode === "signin") {
            return (
                <div>
                    <Login loggedIn={loggedIn}/>
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
    else {
        return (
            <div>
                You are already logged in!
            </div>
        )
    }

 

}

export default Auth;