function redirectHome(){
    if(sessionStorage.getItem("username") != ""){
        window.location.href = "http://localhost:8080/recipes"
    } else {
        window.location.href = "http://localhost:8080/"
    }
}

function showImage(e){
    var image = document.getElementById("imageToUpload")
    image.src = URL.createObjectURL(e.target.files[0])
}

function addColumn(ingredient, amount){
    var table = document.getElementById("tableBody")
    var row = document.createElement("tr")
    var columnName = document.createElement("td")
    var columnNameInput = document.createElement("input")
    columnNameInput.setAttribute("name", "tableIngredient"+i)
    columnNameInput.setAttribute("type", "text")
    columnNameInput.setAttribute("placeholder", "Zutat")
    columnName.appendChild(columnNameInput)

    var columnAmount = document.createElement("td")
    var columnAmountInput = document.createElement("input")
    columnAmountInput.setAttribute("name", "tableAmount"+i)

    columnAmountInput.setAttribute("type", "text")
    columnAmountInput.setAttribute("placeholder", "Menge")
    columnAmount.appendChild(columnAmountInput)

    if(ingredient != undefined){
        columnNameInput.value = ingredient
    }
    if(amount != undefined){
        columnAmountInput.value = amount
    }

    row.appendChild(columnName)
    row.appendChild(columnAmount)
    table.appendChild(row)
    i++
}