import React, { useState } from 'react';
import user_icon from '../../assets/user-regular.svg';
import email_icon from '../../assets/envelope-regular.svg';
import password_icon from '../../assets/lock-solid.svg';
import style from '../LoginSignup/LoginSignup.module.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { toast } from 'react-toastify';
import { HiExclamationCircle } from 'react-icons/hi';


const LoginSignup = () => {
  const [formData, setFormData] = useState({
    username: '',
    email: '',
    password: '',
  });
  const [isSignUp, setIsSignUp] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const validateForm = () => {
    const newErrors = {};
    if (isSignUp && !formData.username.trim()) {
      newErrors.username = 'Username is required';
    }
    if (!formData.email.trim()) {
      newErrors.email = 'Email is required';
    }
    if (!formData.password.trim()) {
      newErrors.password = 'Password is required';
    }
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
  
    if (!validateForm()) {
      return;
    }
  
    setIsLoading(true);
  
    console.log('Signup form data:', formData);
  
    try {
      const url = isSignUp
        ? 'http://localhost:8080/register'
        : 'http://localhost:8080/login';
  
      const response = await axios.post(url, formData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
  
      console.log(response);
  
      
      if (response.data.message) {
        const userResponse = response.data;
  
        toast.success(`Welcome ${userResponse.username}, you have registered successfully`, {
          position: 'top-right',
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
  
        setFormData({ username: '', email: '', password: '' });
        // setIsSignUp(false);
        // if()
        navigate('/dashboard');
      } else {
        
        const errorMessage = response.data.message || 'Registration failed. Please try again.';
        toast.error(errorMessage, {
          position: 'top-right',
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          style: { fontSize: '14px' },
          icon: <HiExclamationCircle style={{ fontSize: '20px' }} />,
        });
      }
    } catch (error) {
      
      if (error.response) {
        
        const errorMessage =
          error.response.data.message || 'An unknown error occurred.';
        toast.error(errorMessage, {
          position: 'top-right',
          autoClose: 3000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
          style: { fontSize: '14px' },
          icon: <HiExclamationCircle style={{ fontSize: '20px' }} />,
        });
      } else if (error.request) {
        
        toast.error('No response from the server. Please try again later.', {
          position: 'top-right',
          autoClose: 3000,
        });
      } else {
        
        toast.error('Something went wrong. Please try again later.', {
          position: 'top-right',
          autoClose: 3000,
        });
      }
      console.error('Error:', error);
    } finally {
      setIsLoading(false);
    }
  };
  

  return (
    <div className={style.main_container}>
      <div className={style.sub_container}>
        <form className={style.container} onSubmit={handleSubmit}>
          <div className={style.header}>
            <div className={style.brand_name}>ice.ly</div>
            <div className={style.text}>{isSignUp ? 'Sign Up' : 'Login'}</div>
            <div className={style.underline}></div>
          </div>

          <div className={style.inputs}>
            
              <div className={style.input}>
                <img src={user_icon} alt="User Icon" />
                <input
                  type="text"
                  placeholder="Enter Username"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  className={errors.username ? style.error : ''}
                />
                {errors.username && <p className={style.error_msg}>{errors.username}</p>}
              </div>
            
            {isSignUp && (
            <div className={style.input}>
              <img src={email_icon} alt="Email Icon" />
              <input
                type="email"
                placeholder="Enter Email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                className={errors.email ? style.error : ''}
              />
              {errors.email && <p className={style.error_msg}>{errors.email}</p>}
            </div>
            )}
            <div className={style.input}>
              <img src={password_icon} alt="Password Icon" />
              <input
                type="password"
                placeholder="Enter Password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                className={errors.password ? style.error : ''}
              />
              {errors.password && <p className={style.error_msg}>{errors.password}</p>}
            </div>
          </div>

          <div className={style.toggle_action}>
            {isSignUp ? (
              <p>
                Already have an account?{' '}
                <span onClick={() => setIsSignUp(false)}>Login</span>
              </p>
            ) : (
              <p>
                Don't have an account?{' '}
                <span onClick={() => setIsSignUp(true)}>Sign Up</span>
              </p>
            )}
          </div>

          <div className={style.submit_container}>
              <button
                type="submit"
                className={`${style.submit} ${isLoading ? style.disabled : ''}`}
                disabled={isLoading}
              >
                {isLoading ? 'Processing...' : isSignUp ? 'Sign Up' : 'Login'}
              </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginSignup;
