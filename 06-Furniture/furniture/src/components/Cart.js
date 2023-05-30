import React, { useState, useEffect } from "react";
import Navbar from "./Navbar";
import OrderApi from "../apis/OrderApi.js";

const Cart = ({ cart }) => {
    const [currentTotal, setCurrentTotal] = useState(0);
    const [discount, setDiscount] = useState(0); // Additional state for possible discounted price
    const [submitted, setSubmitted] = useState({});

    const getTotalCost = () => {
        let total = 0;
        cart.forEach(c => {
            total += c.qty * c.price;
        })
        if (total >= 2000) {
            setDiscount((total - (total * 0.1)).toFixed(2));
        }
        else {
            setDiscount(0);
        }
        setCurrentTotal(total.toFixed(2));
    }

    const submitOrder = () => {
        const userId = sessionStorage.getItem("userId");
        if (userId > 0) {
            let finalOrder = {
                userId: userId,
                items: [],
                total: 0
            };
            if (discount > 0) {
                finalOrder.total = discount;
            }
            else {
                finalOrder.total = currentTotal;
            }


            cart.forEach(c => {
                finalOrder.items.push({
                    itemId: c.id,
                    qty: c.qty
                });
            })
            OrderApi.createOrder(finalOrder);
            setSubmitted(finalOrder);
        }
        else {
            console.log("Error getting userId")
        }


    }

    useEffect(() => {
        getTotalCost();
    }, [])

    if (Object.keys(submitted).length === 0) {
        return (
            <div>
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
                            cart.map(i =>
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
                        {discount ? (<tr>
                            <td className="discount">10% Discount Applied!</td>
                            <td></td>
                            <td></td>
                            <td>{discount}</td>
                        </tr>) : (<></>)}
                    </tfoot>
                </table>
                <button type="button" className="btn btn-primary" onClick={submitOrder}>Submit Order</button>
            </div>
        )
    }
    else {
        return (
            <div>
                <Navbar />
                <h2>Order Submitted!</h2>
                <h3>Your Purchase:</h3>
                <p>Total: {submitted.total}</p>
                <ul>
                    {
                        submitted.items.map(i =>
                            <li key={i.itemId}>{i.qty}{" of Item ID "}{i.itemId}</li>
                            )
                    }

                </ul>
            </div>
        )
    }

}

export default Cart;