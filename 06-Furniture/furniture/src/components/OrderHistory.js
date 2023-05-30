import React, { useState, useEffect } from "react";
import OrderApi from "../apis/OrderApi.js";
import ItemApi from "../apis/ItemApi.js";
import Navbar from "./Navbar.js";

const OrderHistory = () =>
{
    const [orderList, setOrderList] = useState([]);
    const [itemList, setItemList] = useState([]);
    const [userId, setUserId] = useState(0);

    const getItemName = (id) => {
        const item = itemList.find(i => {
            return i.id === id;
        })
        if (item) {
            return item.name;
        } else {
            return "Not found";
        }
    }

    useEffect( () => {
        setUserId(sessionStorage.getItem("userId"));
        OrderApi.getUserOrders(userId, setOrderList);
        ItemApi.getItems(setItemList);

    }, [userId] )

    if (userId > 0) {
        return (
            <div>
                <Navbar />
                <div>
                    <h2>Your Previous Orders</h2>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Order ID</th>
                                <th>Total</th>
                                <th>Items</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                orderList.map( o =>
                                    <tr key={o.id}>
                                        <td>{o.id}</td>
                                        <td>{o.total}</td>
                                        <td><table>
                                            <thead>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Quantity</th>
                                            </thead>
                                            <tbody>
                                            {o.items.map(i =>
                                            <tr key={i.itemId}>
                                                <td>{i.itemId}</td>
                                                <td>{getItemName(i.itemId)}</td>
                                                <td>{i.qty}</td></tr>)}
                                                </tbody>
                                            </table></td>
                                    </tr>)
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
    else {
        return (
            <div>
                 <Navbar />
                 You don't seem to be logged in.
            </div>
        )
    }
    
}

export default OrderHistory;