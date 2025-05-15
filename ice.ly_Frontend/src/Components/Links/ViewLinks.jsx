import React,{useState} from "react";
import style from "../Links/ViewLinks.module.css";

const ViewLinks = () => {

    const books = ["Name of the wind","The Wise Man's Fear", "Kafka on the Shore", "The Master and the Margarita"]

    const [bookList, setBookList]= useState(books)// setBookList updates the state of bookList as given to it by useState()
	const [searchInput, setSearchInput]= useState("");

    const addBook = (event)=>{
        console.log(event)
        event.preventDefault();
        const newBook = event.target[0].value;
		if(newBook.trim()){
			setBookList([...bookList, newBook]);
		} // never update your state directly
         //console.log(newBook);
        event.target[0].value = "";
    }

    const handleDelete = (index)=>{
        setBookList(bookList.filter((_, i)=>(i != index)));
    }

	
	const handleChange = (event)=>{
		setSearchInput(event.target.value);
	};

	const filterBooks = bookList.filter((book)=>
		book.toLowerCase().includes(searchInput.toLowerCase())
	);

  return (
    <div id={style.wrapper}>
    <header>
        <div id={style.linkBanner}>
            <h1 className={style.title}> ice.ly Links</h1>
            <p>All your available links</p>
        </div>
    </header>
       

    <div className={style.link_container}>
        <div className={style.link_header}>
            <strong>Original Link</strong>
            <strong>Generated Link</strong>
            <strong>Date Created</strong>
        </div>
        <div className={style.link_details}>
            <p><a href="https://example.com" target="_blank">https://example.com</a></p>
            <p><a href="https://generated-link.com" target="_blank">https://generated-link.com</a></p>
            <p> January 4, 2025, 12:30 PM</p>
            <span className={style.delete}>delete</span>
        </div>
    </div>
    </div>
  )
}

export default ViewLinks