async function loadData() {

    const popup = Notification();

    const response = await fetch("LoadProductData");

    if (response.ok) {

        const json = await response.json();

        if (json.status) {

            console.log(json);

            loadOptions("brand", json.brandList, "name");
            loadOptions("condition", json.qualityList, "value");
            loadOptions("color", json.colorList, "value");
            loadOptions("storage", json.stroageList, "value");


            popup.success({message: "Success"});

        } else {

//            window.location = "index.html";

            popup.error({message: "Smothing went wrong"});

        }

    } else {

//        window.location = "index.html";

        popup.error({message: "Smothing went wrong"});

    }

}

function loadOptions(prefix, dataList, property) {

    let options = document.getElementById(prefix + "-options");
    let li = document.getElementById(prefix + "-li");
    options.innerHTML = "";


    dataList.forEach(item => {

//true -> juest list and others tags / false -> only list 

        let li_clone = li.cloneNode(true);

        if (prefix == "color") {

            li_clone.style.borderColor = "black";
            li_clone.querySelector("#" + prefix + "-a").style.backgroundColor = item[property];

        } else {

            li_clone.querySelector("#" + prefix + "-a").innerHTML = item[property];

        }

        options.appendChild(li_clone);

    });

    const  all_li = document.querySelectorAll("#" + prefix + "-options li");

    all_li.forEach(list => {

        list.addEventListener("click", function () {

            all_li.forEach(y => {

                y.classList.remove("chosen");

            });

            this.classList.add("chosen");

        });

    });

}

//product sorting process

async function searchProduct(firstResult) {

    const popup = new Notification();

    const brand_name = document.getElementById("brand-options").querySelector(".chosen")?.querySelector("a").innerHTML; //optional chaning > access if exists
    const condition_name = document.getElementById("condition-options").querySelector(".chosen")?.querySelector("a").innerHTML; //optional chaning > access if exists
    const color_name = document.getElementById("color-options").querySelector(".chosen")?.querySelector("a").style.backgroundColor; //optional chaning > access if exists
    const storage_value = document.getElementById("storage-options").querySelector(".chosen")?.querySelector("a").innerHTML;

    console.log(brand_name);
    console.log(condition_name);
    console.log(color_name);
    console.log(storage_value);

    const price_range_start = $("#slider-range").slider("values", 0)//left
    const price_range_end = $("#slider-range").slider("values", 1)//right

    console.log(price_range_start);
    console.log(price_range_end);

    const sort_value = document.getElementById("st-sort").value;

    //console.log(sort_value);

    const data = {

        firstResult: firstResult,
        brandName: brand_name,
        conditionName: condition_name,
        colorName: color_name,
        storageValue: storage_value,
        priceStart: price_range_start,
        priceEnd: price_range_end,
        sortValue: sort_value

    };

    const dataJSON = JSON.stringify(data);

    const response = await fetch(
            "SearchProducts",
            {
                method: "POST",
                headers: {

                    "Content-Type": "application/json"

                },
                body: dataJSON
            }

    );

    if (response.ok) {

        console.log("Ok");

        const json = await response.json();

        if (json.status) {

            console.log(json);
            
            updateProductView(json.productList);
            
            popup.success({
                
                message:"Product filtering Success!"
                
            });

        } else {

            popup.error({

                message: "Something went wrong. Please try again"

            });

        }

    } else {

        popup.error({

            message: "Something went wrong. Please try again"

        });

    }

}

//for show products
function updateProductView(productList){
    
    
    
}