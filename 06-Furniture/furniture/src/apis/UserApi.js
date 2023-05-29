// For getting and creating user data

const URI = "http://localhost:3000/users";

const UserApi = {
    getUsers: (setUserList) => { // only for admin use
        fetch(URI)
            .then((result) => {
                console.log(result);
                return result.json();
            })
            .then((data) => {
                setUserList(data);
            })
            .catch((error) => { console.log(error) });
    },

    getUserById: (setUser, userId) => {
        fetch((URI + "/" + userId))
        .then((result) => {
            console.log(result);
            return result.json();
        })
        .then((data) => {
            setUser(data);
        })
        .catch((error) => { console.log(error)});
    },

    getLogin: (email, password) => {
        fetch((URI + "?email=" + email + "&password=" + password))
        .then((result) => {
            console.log(result);
            return result.json();
        })
        .then((data) => {
            console.log(data);
            if (data.length === 1) {
               sessionStorage.setItem("userId", data[0].id);
            }
            else {
                throw new Error("Login failed");
            }
        })
    },

    createUser: (userToCreate) => {
        fetch(URI, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userToCreate)
        })
            .then(result => result.json())
            .then(data => {
                console.log("User Created");
                console.log(data);
                sessionStorage.setItem("userId", data.id);
            })
            .catch((error) => { console.log(error) })
    },

    deleteUser: (userId) => {
        fetch((URI + "/" + userId), {
            method: "DELETE"
        })
            .then(result => result.json())
            .then(data => {
                console.log("User Deleted");
                console.log(data);
            })
            .catch((error) => { console.log(error) })
    },

    updateUser: (userToUpdate) => {
        fetch(URI, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(userToUpdate)
        })
        .then(result => result.json())
        .then(data => {
            console.log("User Updated");
            console.log(data);
        })
        .catch((error) => { console.log(error)})
    }
}

export default UserApi;