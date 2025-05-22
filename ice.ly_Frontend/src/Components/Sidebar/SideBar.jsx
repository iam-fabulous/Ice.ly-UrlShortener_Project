import React from 'react'
import style from '../../Components/Sidebar/SideBar.module.css'
import avatar from '../../assets/user-solid.svg'
import { Link } from 'react-router-dom'

const SideBar = () => {
  return (
    <div className={style.sideBarMain}>
        <div className={style.sideBarContainer}>
            <div className={style.user_details}>
                <img src={avatar} alt="User Avatar" className={style.avatar}/>
                <h3 className={style.username}>John Doe</h3>
                <p className={style.user_id}>User ID: #123456</p>
                <p className={style.email}>Email: john.doe@example.com</p>
            </div>
        
            <button  className={style.myBtn}><Link to="/dashboard" className={style.custom_link}><p>Home</p></Link></button>
            <button className={style.myBtn}><Link to="/viewlinks" className={style.custom_link}><p>Links</p></Link></button>
            <button className={style.myBtn}><Link to="/" className={style.custom_link}><p>Logout</p></Link></button>
        </div>
    </div>
  )
}

export default SideBar