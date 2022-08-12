
import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";

function App() {
  const [message, setMessage] = useState([]);

  useEffect(() => {
    fetch("/hello")
        .then((response) => {
      return response.json();
    })
        .then((data) => {
          setMessage(data);
        });
  }, []);

  return (
    <div className="App">
      <header className="App-header">
          <h2>
              MP3 스트리밍 테스트입니다.
          </h2>
        <ul>
          {message.map((text, index) => <li key={`${index}-${text}`}>{text}</li>)}
        </ul>
      </header>
    </div>
  );
}

export default App;
