import React, { useState } from 'react';
import UserApi from '../apis/UserApi';
import { Navigate } from "react-router-dom";


const Register = (userId, setUserId) => {
    const [userName, setUserName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loggedIn, setLoggedIn] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const user = {
            "name": userName,
            "email": email,
            "password": password
        }

        try {
            await UserApi.createUser(user);
        }
        catch (error) {
            console.log("Error registering")
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
                    <h1 className="h3 mb-3 fw-normal">Register</h1>
                    <div className="form-floating">
                        <input type="text"
                            className="form-control"
                            id="floatingInput"
                            placeholder="First Last"
                            value={userName}
                            onChange={(event) => { setUserName(event.target.value) }}>
                        </input>
                        <label htmlFor="floatingInput">Your name</label>
                    </div>
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
                    <button className="w-100 btn btn-lg btn-primary" type="submit">Create Account</button>
                </form>
            </main >
        )
    } 

}

export default Register;