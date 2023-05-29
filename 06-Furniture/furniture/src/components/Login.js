import React, { useState } from 'react';
import UserApi from '../apis/UserApi';
import { Navigate } from "react-router-dom";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loggedIn, setLoggedIn] = useState(false);
    
    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            await UserApi.getLogin(email, password);
        } catch (error) {
            console.log("Error logging in");
            return;
        }
        
        setLoggedIn(true);
    }
    if (loggedIn) {
        return (<Navigate to="/browse" replace={true} />);
    }
    else {
        return (
            <main className="form-signin w-100 m-auto">
                <form onSubmit={handleSubmit}>
                    <h1 className="h3 mb-3 fw-normal">Please sign in</h1>
                    <div className="form-floating">
                        <input type="email"
                            className="form-control"
                            id="floatingInput"
                            placeholder="name@example.com"
                            value={email}
                            onChange={(event) => { setEmail(event.target.value) }}>
                            </input>
                        <label htmlFor="floatingInput">Email address</label>
                    </div>
                    <div className="form-floating">
                        <input type="password" 
                        className="form-control" 
                        id="floatingPassword" 
                        placeholder="Password"
                        value={password}
                        onChange={(event) => { setPassword(event.target.value) }}>
                        </input>
                        <label htmlFor="floatingPassword">Password</label>
                    </div>
    
                    <div className="checkbox mb-3">
                        <label>
                            <input type="checkbox" value="remember-me"></input> Remember me
                        </label>
                    </div>
                    <button className="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
                </form>
            </main >
        )
    }
    
}

export default Login;