const url = "http://localhost:3000/"

let userId = "";

function renderMenu() {
    if (localStorage.currentUser) {
        userId = localStorage.getItem("currentUser");
        document.getElementById("mainbody").innerHTML = "<div class='form'>What Do You Want to Do?<br>" +
        "<button onclick='renderForm(\"deposit\")'>Deposit Funds</button><br>" +
        "<button onclick='renderForm(\"withdrawl\")'>Withdraw Funds</button><br>" +
        "<button onclick='renderForm(\"transfer\")'>Transfer Funds</button></div>";
    }
    else {
        document.getElementById("mainbody").innerHTML = "You're not logged in!";
    }
}


async function renderForm(type) {
    let form = "<div class='form'>";
    if (type === "deposit") {
        form += "<h3>Deposit</h3>"
    }
    else if (type === "withdrawl") {
        form += "<h3>Withdrawl</h3>"
    }
    else if (type === "transfer") {
        form += "<h3>Transfer</h3>"
    }
    let accounts = await getAccounts();

    if (type === "transfer") {
        form += "Account To Transfer From: <select name='accountsFrom' id='accountsFrom'>"
        accounts.forEach(a => {
            form += "<option value='" + a.id + 
            "'>Account #" + a.id + ". Type: " + a.type + ". Balance: $" + a.balance + "</option>" 
        })
        form += "</select><br>Account To Transfer To: <select name='accountsTo' id='accountsTo'>"
        accounts.forEach(a => {
            form += "<option value='" + a.id + 
            "'>Account #" + a.id + ". Type: " + a.type + ". Balance: $" + a.balance + "</option>" 
        }) 
    } else {
        form += "Account: <select name='accounts' id='accounts'>"
        accounts.forEach(a => {
            form += "<option value='" + a.id + 
            "'>Account #" + a.id + ". Type: " + a.type + ". Balance: $" + a.balance + "</option>" 
        })
    }
    
    form += "</select><br>Amount: $<input type='number' id='amount' step='0.01' min='0' placeholder='500.00'/><br>" +
    "<button onclick='renderMenu()'>Cancel</button>";
    if (type === "deposit") {
        form += "<button onclick='submitDeposit()'>Submit</button></div>";
    }
    else if (type === "withdrawl") {
        form += "<button onclick='submitWithdrawl()'>Submit</button></div>";
    }
    else if (type === "transfer") {
        form += "<button onclick='submitTransfer()'>Submit</button></div>";
    } 
    document.getElementById("mainbody").innerHTML = form;
}

// Gets the current user's accounts
async function getAccounts() {
    const query = url + "accounts?userId=" + userId;
    try {
        const response = await fetch(query);
        const jsonData = await response.json();
        console.log(jsonData);
        if (jsonData) {
            return jsonData;
        }
        else {
            console.log("Error getting accounts");
        }
    } catch (error) {
        console.log(error);
    }
}


async function getAccountById(id) {
    const query = url + "accounts/" + id;
    try {
        const response = await fetch(query);
        const jsonData = await response.json();
        console.log(jsonData);
        if (jsonData) {
            return jsonData;
        }
        else {
            console.log("Error getting account");
        }
    } catch (error) {
        console.log(error);
    }
}


// Note: a transaction requires a POST to transactions and a PATCH to accounts

async function submitDeposit() {
    const accountId = document.querySelector('#accounts').value;
    const type = "Deposit";
    const amount = document.getElementById("amount").value;

    const transaction = {
        "type": type,
        "amount": amount,
        "userId": userId,
        "accountId": accountId
    }

    let thisAccount = await getAccountById(accountId);

    if (await postTransaction(transaction)) {
        let oldBalance = Number(thisAccount.balance);
        let balance = oldBalance += Number(amount);
        let accountUpdate = {
            "balance": balance
        };
        if (await updateAccount(accountUpdate, accountId)) {
            document.getElementById("mainbody").innerHTML = "<p>Transaction Succesful!</p>";
        }
    }
}

async function submitWithdrawl() {
    const accountId = document.querySelector('#accounts').value;
    const type = "Withdrawl";
    const amount = document.getElementById("amount").value;

    const transaction = {
        "type": type,
        "amount": amount,
        "userId": userId,
        "accountId": accountId
    }

    let thisAccount = await getAccountById(accountId);

    if (await postTransaction(transaction)) {
        let oldBalance = Number(thisAccount.balance);
        let balance = oldBalance -= Number(amount);
        let accountUpdate = {
            "balance": balance
        };
        if (await updateAccount(accountUpdate, accountId)) {
            document.getElementById("mainbody").innerHTML = "<p>Transaction Succesful!</p>";
        }
    }
}

async function submitTransfer() {
    const accountIdFrom = document.querySelector('#accountsFrom').value;
    const accountIdTo = document.querySelector('#accountsTo').value;
    const type = "Transfer";
    const amount = document.getElementById("amount").value;
    const transactionOut = {
        "type": type,
        "amount": amount,
        "userId": userId,
        "accountId": accountIdFrom
    }
    const transactionIn = {
        "type": type,
        "amount": amount,
        "userId": userId,
        "accountId": accountIdTo
    }

    let accountFrom = await getAccountById(accountIdFrom);
    let accountTo = await getAccountById(accountIdTo);

    // update "from" account
    await postTransaction(transactionOut);
    let oldBalance = Number(accountFrom.balance);
    let balance = oldBalance -= Number(amount);
    let accountUpdateFrom = {
            "balance": balance
    };
    await updateAccount(accountUpdateFrom, accountIdFrom);

     // update "to" account
     await postTransaction(transactionIn);
     oldBalance = Number(accountTo.balance);
     balance = oldBalance += Number(amount);
     let accountUpdateTo = {
             "balance": balance
     };
     if (await updateAccount(accountUpdateTo, accountIdTo)) {
        document.getElementById("mainbody").innerHTML = "<p>Transaction Succesful!</p>";
     }
    
}

async function postTransaction(transaction) {
    const query = url + "transactions";
    try {
        const response = await fetch(query, {
            method: 'POST',
            body: JSON.stringify(transaction),
            headers: {
            'Content-type': 'application/json; charset=UTF-8',
        }
    }) 
        const jsonData = await response.json();
        if (jsonData) {
            console.log(jsonData);
            return true;
        }
        else {
            console.log("Error posting this transaction!");
            return false;
        }
    } catch (error) {
        console.log(error);
    }
}

async function updateAccount(updates, accountId) {
    const query = url + "accounts/" + accountId;
    try {
        const response = await fetch(query, {
            method: 'PATCH',
            body: JSON.stringify(updates),
            headers: {
            'Content-type': 'application/json; charset=UTF-8',
        }
    }) 
        const jsonData = await response.json();
        if (jsonData) {
            console.log(jsonData);
            return true;
        }
        else {
            console.log("Error making this update!");
            return false;
        }
    } catch (error) {
        console.log(error);
    }
}