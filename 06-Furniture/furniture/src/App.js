import logo from './logo.svg';
import './App.css';
import Auth from './components/Auth.js';
import Home from './components/Home.js';
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
    <Routes>
      <Route path="/" element={<Home />} exact/>
      <Route path="/auth" element={<Auth />} />
    </Routes>
    </BrowserRouter>
  );
}

export default App;
