import React from 'react';
import Navbar from './Navbar.js';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
    <div>
        <Navbar />
        <h2>Beautiful Furniture for All Needs</h2>
       
        {sessionStorage.getItem("userId") > 0 ? (<p>Welcome back!</p>)  : 
        ( <div style={{textAlign:"center"}}>To get access to our exclusive deals, sign up today!<br/> 
        <Link to="/auth"><button type="button" className="btn btn-primary">Login or Register</button></Link>
        </div>
       )}
    </div>
    )
    
}

export default Home;