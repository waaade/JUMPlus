// Functions for sending orders to API and getting previous orders

const URI = "http://localhost:3000/orders"

const OrderApi = {
    getOrders: (setOrderList) => {
        fetch(URI)
        .then( (result) => {
            console.log(result);
            return result.json();
        })
        .then( (data) => {
            setOrderList(data);
        })
        .catch( (error) => { console.log(error)});
    },

    createOrder: (orderToCreate) => {
        fetch(URI, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(orderToCreate)
        })
        .then( result => result.json() )
        .then( data => {
            console.log("Order Created");
            console.log(data);

        })
    }
}

export default OrderApi;