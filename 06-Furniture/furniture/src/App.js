import logo from './logo.svg';
import './App.css';
import Auth from './components/Auth.js';
import Home from './components/Home.js';
import FurnitureGrid from './components/FurnitureGrid.js';
import OrderHistory from './components/OrderHistory.js';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import React, { useState } from 'react';

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} exact />
        <Route path="/auth" element={<Auth />} />
        <Route path="/browse" element={<FurnitureGrid />} />
        <Route path="/orders" element={<OrderHistory />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
