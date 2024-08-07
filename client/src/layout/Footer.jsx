import React from 'react';
import { Link } from 'react-router-dom';
import './Footer.css';

const Footer = () => {
  return (
    <footer className="footer">
      <div className="container d-flex justify-content-between">
        <div className="left">
          <Link to="/">
            <img src="/img/footer/Goodz_logo2.png" alt="GoodZ Logo" />
          </Link>
          <div className="social-icons d-flex">
            <a href="https://www.facebook.com/?locale=ko_KR" target="_blank" rel="noopener noreferrer">
              <img src="/img/footer/facebook_Icon.png" alt="Facebook" />
            </a>
            <a href="https://kr.linkedin.com/" target="_blank" rel="noopener noreferrer">
              <img src="/img/footer/linkedin_icon.png" alt="LinkedIn" />
            </a>
            <a href="https://www.youtube.com/@alohaclass" target="_blank" rel="noopener noreferrer">
              <img src="/img/footer/youtube_icon.png" alt="YouTube" />
            </a>
            <a href="https://www.instagram.com/eeeeee.s/" target="_blank" rel="noopener noreferrer">
              <img src="/img/footer/instagram_Icon.png" alt="Instagram" />
            </a>
          </div>
        </div>
        <div className="links">
          <h5 style={{ fontWeight: 'bold' }}>이용정책</h5>
          <div className="link-wrapper">
            <Link to="/footer/1">개인정보처리방침</Link>
            <Link to="/footer/2">검수기준</Link>
            <Link to="/footer/3">커뮤니티 가이드라인</Link>
          </div>
        </div>
        <div className="right">
          <p>고객센터 1234-5678</p>
          <p>운영시간 평일 09:30 - 18:20 (토 / 일 공휴일 휴무)</p>
          <p>점심시간 평일 13:00 - 14:00</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
