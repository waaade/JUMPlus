import React, { useState } from 'react';

const FurnitureItem = ({ id, name, description, price, initQty, addToCart, subtractFromCart }) => {
    const [qty, setQty] = useState(initQty);
    const plusQty = () => {
        setQty(qty + 1);
        addToCart(id, name, price);
    }
    const minusQty = () => {
        if (qty > 0) {
            setQty(qty - 1);
            subtractFromCart(id);
        }
    }
    return (
        <div className="card">
            <img src="..." className="card-img-top" alt="..."></img>
            <div className="card-body">
                <h5 className="card-title">{name}</h5>
                <p className="card-text">{description}</p>
                <p className="card-text">{price}</p>
                <p className="card-text">{qty}</p>
                <button onClick={plusQty} className="btn btn-primary">Add</button>
                <button onClick={minusQty} className="btn btn-secondary">Remove</button>
            </div>
        </div>
    )
}

export default FurnitureItem;