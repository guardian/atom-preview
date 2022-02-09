import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BannerComponent} from "./Banner";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload. By Grdian IV DevMode Check again
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
        <BannerComponent/>
      </header>
    </div>
  );
}

export default App;
