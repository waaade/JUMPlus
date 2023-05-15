const url = "http://localhost:3000/"

let userId = "";

renderMenu();

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
    form += "Account: <select name='accounts' id='accounts'>"
    accounts.forEach(a => {
        form += "<option value='" + a.id + 
        "'>Account #" + a.id + ". Type: " + a.type + ". Balance: $" + a.balance + "</option>" 
    })
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
    
    await postTransaction(transaction);

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
        }
        else {
            console.log("Error posting this transaction!");
        }
    } catch (error) {
        console.log(error);
    }
}