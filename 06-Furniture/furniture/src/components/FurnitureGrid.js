// View of all furniture items for easy shopping
import React, { useState, useEffect, useRef } from 'react';
import Navbar from './Navbar.js';
import FurnitureItem from './FurnitureItem.js';
import ItemApi from '../apis/ItemApi.js';
import Cart from './Cart.js';

const FurnitureGrid = () => {
    const [itemList, setItemList] = useState([]);
    let cartItems = useRef([]); // ref is array for easily storing item quantities
    const [showCart, setShowCart] = useState(false); 
    const [cart, setCart] = useState([]); // state only used when cart is viewed

    const addToCart = (id, name, price) => {
        // check if item exists in cart already, add or update accordingly
        let index = getIndex(id);
        if (index > -1) {
            cartItems.current[index].qty += 1;
        }
        else {
            cartItems.current.push({ id: id, name: name, price: price, qty: 1 });
        }
        console.log(cartItems.current);
    }

    const subtractFromCart = (id) => {
        let index = getIndex(id);
        if (index > -1) {
            if (cartItems.current[index].qty > 0) {
                cartItems.current[index].qty -= 1;
            }
        }
    }
    // Find index of item in carItems array from its id
    const getIndex = (id) => {
        return cartItems.current.findIndex(i => i.id === id);
    }

    // Get quantity from ref (retained if re-render occurs from accessing cart)
    const getQty = (id) => {
        let index = getIndex(id);
        if (index > -1) {
            return cartItems.current[index].qty;
        }
        else {
            return 0;
        }
    }

    const checkout = () => {
        setCart(cartItems.current);
        setShowCart(true);
    }

    useEffect(() => {
        ItemApi.getItems(setItemList);
    }, [])


    if (!showCart) {
        return (
            <>
                <Navbar />
                <h2>Check out these items!</h2>
                <div className="container text-center">
                    {
                        itemList.map(i =>
                            <FurnitureItem key={i.id}
                                id={i.id}
                                name={i.name}
                                description={i.description}
                                price={i.price}
                                initQty={getQty(i.id)} // retain qty if cart is accessed
                                addToCart={addToCart}
                                subtractFromCart={subtractFromCart}
                            />)
                    }

                </div>
                <button type="button" className="btn btn-primary" onClick={checkout}>Go to Cart</button>
            </>
        )
    }
    else {
        return (
            <>
                <Cart cart={cart} />
                <br/><button type="buttopn" className="btn btn-primary" onClick={()=>setShowCart(false)}>Go Back</button>
            </>
        )
    }

}

export default FurnitureGrid;