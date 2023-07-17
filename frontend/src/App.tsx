import React, {useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';

interface TestData {
    testString: string;
    property: boolean;

}
function App() {
  const [data, setData] = useState<TestData | null>(null);
  console.log("The data", data)
  useEffect(() => {
    // Fetch request
    fetch('/testPath/internetcheckingthings')
        .then(response => response.json())
        .then(data => {
          // Handle the received data
          console.log(data);
          setData(data)
        })
        .catch(error => {
          // Handle any errors
          console.error(error);
        });

  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
          <p>
              Edit <code>src/App.tsx</code> {data && data.testString} and save to reload. By Grdian IV DevMode Check again
          </p>
          {data && <p>Property: {data.property.toString()}</p>}
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
      </header>
    </div>
  );
}

export default App;
