import React, { useState, useEffect } from 'react';
import Navbar from './Navbar.js';
import { Link } from 'react-router-dom';

const Home = () => {
    const [userId, setUserId] = useState(0);
    const logout = () => {
        sessionStorage.clear();
        setUserId(0);
    }

    useEffect( () => {
        setUserId(sessionStorage.getItem("userId"));
    }, [])
    return (
    <div>
        <Navbar />
        <h2>Beautiful Furniture for All Needs</h2>
       
        {userId > 0 ? (<><p>Welcome back!</p>
        <button type="button" onClick={logout} className="btn btn-primary">Logout</button></>)  : 
        ( <div style={{textAlign:"center"}}>To get access to our exclusive deals, sign up today!<br/> 
        <Link to="/auth"><button type="button" className="btn btn-primary">Login or Register</button></Link>
        </div>
       )}
    </div>
    )
    
}

export default Home;