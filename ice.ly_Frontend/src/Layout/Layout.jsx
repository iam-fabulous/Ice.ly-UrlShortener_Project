import React from 'react'
import LoginSignup from '../Auth/LoginSignup/LoginSignup'
import {Outlet} from "react-router-dom"
import HomePage from '../Components/HomePage/HomePage'
import style from '../Layout/Layout.module.css'
import SideBar from '../Components/Sidebar/SideBar'
import ViewLinks from '../Components/Links/ViewLinks'


function Layout () {
  return (
    // <div>
    //     <HomePage/>
    //     <SideBar/>
    //             {/* <div className={style.outletContainer}>
                    
    //                 <Outlet/>
    //             </div> */}
    //       <ViewLinks/>
    // </div>
    <div className={style.layout}>
      <div className={style.sidebar}>
        <SideBar />
      </div>
      <div className={style.mainContent}>
        <HomePage />
        <ViewLinks />
      </div>
    </div>
  )
}

export default Layout