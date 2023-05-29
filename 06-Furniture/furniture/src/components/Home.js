import React, {useState} from 'react';
import Navbar from './Navbar.js';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
    <div>
        <Navbar />
        <h2>Beautiful Furniture for All Needs</h2>
        <p>To get access to our exclusive deals, sign up today!</p>
        <Link to={`/auth`}>Login</Link>
    </div>
    )
    
}

export default Home;