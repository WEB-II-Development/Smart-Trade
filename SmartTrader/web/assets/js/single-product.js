/* global NumberFormat */

async function loadData() {

    const SearchParams = new URLSearchParams(window.location.search);

    if (SearchParams.has("id")) {

        const productId = SearchParams.get("id");

        console.log(productId);

        const response = await fetch("LoadSingleProduct?id=" + productId);

        if (response.ok) {

            const json = await response.json();

            if (json.status) {

                console.log(json);

                // single product images
                document.getElementById("image1").src = "product-images\\" + json.product.id + "\\image1.png";
                document.getElementById("image2").src = "product-images\\" + json.product.id + "\\image2.png";
                document.getElementById("image3").src = "product-images\\" + json.product.id + "\\image3.png";
                document.getElementById("thumb-image1").src = "product-images\\" + json.product.id + "\\image1.png";
                document.getElementById("thumb-image2").src = "product-images\\" + json.product.id + "\\image2.png";
                document.getElementById("thumb-image3").src = "product-images\\" + json.product.id + "\\image3.png";

                //product Features
                document.getElementById("product-title").innerHTML = json.product.title;
                document.getElementById("published-on").innerHTML = json.product.created_at;
//                document.getElementById("product-price").innerHTML = json.product.price;
                document.getElementById("product-price").innerHTML = new Intl.NumberFormat("en-US", {minimumFractionDigits: 2}).format(json.product.price);
                document.getElementById("brand-name").innerHTML = json.product.model.brand.name;
                document.getElementById("model-name").innerHTML = json.product.model.name;
                document.getElementById("product-quality").innerHTML = json.product.condition.value;
                document.getElementById("product-stock").innerHTML = json.product.qty;

                //product Colour
                document.getElementById("color-border").style.borderColor = json.product.color.value;
                document.getElementById("color-background").style.backgroundColor = json.product.color.value;

                document.getElementById("product-stroage").innerHTML = json.product.stroage.value;
                document.getElementById("product-description").innerHTML = json.product.description;

                //add-to-cart-main-button
                const addToCartMain = document.getElementById("add-to-cart-main");
                addToCartMain.addEventListener(
                        "click", (e) => {

                    addToCart(json.product.id, document.getElementById("add-to-cart-qty").value);

                    e.preventDefault();

                });

                //Similer-Product

                let similer_product_main = document.getElementById("smiler-product-main");
                let productHtml = document.getElementById("similer-product");

                similer_product_main.innerHTML = "";

                json.productList.forEach(item => {

//                    let productCloneHtml = productHtml.cloneNode(true);
//
//                    productCloneHtml.querySelector("#similer-product-a1").href = "single-product.html?id=" + item.id;
//                    productCloneHtml.querySelector("#similer-product-image").src = "product-images\\" + item.id + "\\image1.png";
//                    productCloneHtml.querySelector("#similer-product-add-to-cart").addEventListener(
//                            "click", (e) => {
//                        addToCart(item.id, 1);
//                        e.preventDefault();
//                    });
//
//                    productCloneHtml.querySelector("#similer-product-a2").href = "single-product.html?id=" + item.id;
//                    productCloneHtml.querySelector("#similer-product-title").innerHTML = item.title;
//                    productCloneHtml.querySelector("#similer-product-stroage").innerHTML = item.stroage.value;
//                    productCloneHtml.querySelector("#similer-product-price").innerHTML = new Intl.NumberFormat("en-US", {minimumFractionDigits: 2}).format(item.price);
//                    productCloneHtml.querySelector("#similer-product-color-border").style.borderColor = item.color.value;
//                    productCloneHtml.querySelector("#similer-product-color-background").style.backgroundColor = item.color.value;
//
//                    similer_product_main.appendChild(productCloneHtml);

                    let similerProductDesign = `<div class="slick-single-layout" id="similer-product">
                            <div class="axil-product">
                                <div class="thumbnail">
                                    <a href="${'single-product.html?id=' + item.id}" id="similer-product-a1">
                                        <img src="${'product-images\\' + item.id + '\\image1.png'}" id="similer-product-image" alt="Product Images">
                                    </a>

                                    <div class="product-hover-action">
                                        <ul class="cart-action">
                                            <li class="wishlist"><a href="#"><i class="far fa-heart"></i></a></li>
                                            <li class="select-option"><a id="similer-product-add-to-cart" onclick="addToCart(${item.id},1)">Add to Cart</a></li>
                                            <li class="quickview"><a href="single-product.html?id=${item.id}" id="similer-product-a2"><i class="far fa-eye"></i></a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="product-content">
                                    <div class="inner">
                                        <h5 class="title"><a href="#" id="similer-product-title">${item.title}</a></h5>
                                        <p class="b2 mb--10" id="similer-product-stroage">${item.stroage?.value || 'N/A'}</p>
                                        <div class="product-price-variant">
                                            <span class="price current-price" id="similer-product-price">Rs. ${new Intl.NumberFormat(
                            "en-US",
                            {minimumFractionDigits: 2})
                            .format(item.price)}</span>
                                        </div>
                                        <div class="color-variant-wrapper">
                                            <ul class="color-variant">
                                                <li class="color-extra-01 active">
                                                    <!-- color-border and color-background -->
                                                    <span id="similer-product-color-border" style="black">
                                                        <span class="color" id="similer-product-color-background" style="background-color:${item.color.value}">
                                                        </span>
                                                    </span> 
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>`;
                    similer_product_main.innerHTML += similerProductDesign;

                });



                $('.recent-product-activation').slick({
                    infinite: true,
                    slidesToShow: 4,
                    slidesToScroll: 4,
                    arrows: true,
                    dots: false,
                    prevArrow: '<button class="slide-arrow prev-arrow"><i class="fal fa-long-arrow-left"></i></button>',
                    nextArrow: '<button class="slide-arrow next-arrow"><i class="fal fa-long-arrow-right"></i></button>',
                    responsive: [{
                            breakpoint: 1199,
                            settings: {
                                slidesToShow: 3,
                                slidesToScroll: 3
                            }
                        },
                        {
                            breakpoint: 991,
                            settings: {
                                slidesToShow: 2,
                                slidesToScroll: 2
                            }
                        },
                        {
                            breakpoint: 479,
                            settings: {
                                slidesToShow: 1,
                                slidesToScroll: 1
                            }
                        }
                    ]
                });

            } else {

                window.location = "index.html";

            }

        } else {

            window.location = "index.html";

        }

    }

}

function addToCart(productId, qty) {

    console.log(productId + " " + qty);

}