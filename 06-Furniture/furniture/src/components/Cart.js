import React, { useState, useEffect } from "react";
import Navbar from "./Navbar";

const Cart = ({cart}) => {
    const [currentTotal, setCurrentTotal] = useState(0);
    const [discount, setDiscount] = useState(0); // Additional state for possible discounted price
    
    const getTotalCost = () => {
        let total = 0;
        cart.forEach(c => {
            total += c.qty * c.price;
        })
        if (total >= 2000) {
            setDiscount((total - (total * 0.1)).toFixed(2));
        }
        setCurrentTotal(total.toFixed(2));
    }
    
    
    useEffect(() => {
        getTotalCost();
    }, [])
    
    return (
        <>
        <Navbar />
        
        <table className="table">
            <thead>
                <tr>
                    <th>Item Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>

                </tr>
            </thead>
            <tbody>
                {
                    cart.map( i =>
                        <tr key={i.id}>
                        <td>{i.name}</td>
                        <td>{i.price}</td>
                        <td>{i.qty}</td>
                        <td>{(i.qty * i.price).toFixed(2)}</td>
                        </tr>
                        )
                }
            </tbody>
            <tfoot>
                <tr>
                    <td>Total of All Items</td>
                    <td></td>
                    <td></td>
                    <td>{currentTotal}</td>
                </tr>
                { discount ? (<tr>
                    <td className="discount">10% Discount Applied!</td>
                    <td></td>
                    <td></td>
                    <td>{discount}</td>
                </tr>) : (<></>)}
            </tfoot>
        </table>
        </>
    )
}

export default Cart;