// Functions for getting items from API

const URI = "http://localhost:3000/items";

const ItemApi = {
    getItems: (setItemList) => {
        fetch(URI)
        .then( (result) => {
            console.log(result);
            return result.json();
        })
        .then( (data) => {
            setItemList(data);
        })
        .catch( (error) => { console.log(error)});
    },

    createItem: (itemToCreate) => {
        fetch(URI, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(itemToCreate)
        })
        .then( result => result.json() )
        .then( data => {
            console.log("Item Created");
            console.log(data);

        })
        .catch( (error) => { console.log(error) })
    }
}

export default ItemApi;