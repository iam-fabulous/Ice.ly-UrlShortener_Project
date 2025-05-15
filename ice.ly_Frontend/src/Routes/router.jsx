import LoginSignup from "../Auth/LoginSignup/LoginSignup";
import HomePage from "../Components/HomePage/HomePage.jsx";
import Layout from "../Layout/Layout.jsx";
import ViewLinks from "../Components/Links/ViewLinks.jsx"
import SideBar from "../Components/Sidebar/SideBar.jsx";


export const Router = [

    {
        path: "/",
        element:<HomePage/>
    },
    {
        path: "/login/signup",
        element: <LoginSignup/>
    },
    {
        path: "/viewlinks",
        element: <ViewLinks/>
    },
    {
        path: "/sidebar",
        element: <SideBar/>
    },
    {
        path: '/dashboard',
        element: <Layout/>,
        children: [
            // {
            //     path: "/movie/popular",
            //     element: 
            // },
            // {
            //     path: "/movie/upcoming",
            //     element: 
            // },
            // {
            //     path: "/movie/nowPlaying",
            //     element: 
            // }
        ]
    }
]

export default Router