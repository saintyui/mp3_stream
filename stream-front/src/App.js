
import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import Datatable from "./component/Datatable";
import AudioPlayer from "react-h5-audio-player";
import 'react-h5-audio-player/lib/styles.css';


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
          <Datatable></Datatable>
          <AudioPlayer />
      </header>
    </div>
  );
}

export default App;
