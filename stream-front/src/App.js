
import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import Datatable from "./component/Datatable";
import AudioPlayer from "react-h5-audio-player";
import 'react-h5-audio-player/lib/styles.css';


function App() {
    const [mp3List, setMp3List] = useState([]);
    const [mp3ListLength, setMp3ListLength] = useState(0);
    const [id, setId] = useState(1);
    const [url, setUrl] = useState("/mp3/stream/1");
    const [songTitle, setSongTitle] = useState('');

    useEffect(async () => {
        const response = await fetch("/mp3/list", {
            headers : {
                'Content-Type': 'application/json',
                'Accept': 'application/json'}
        });
        if (!response.ok){
            throw new Error('뭔가 잘못된 거 같아');
        }

        const data = await response.json();
        //console.log(data);
        const list = data.map(mp3Data => {
            return{
                id: mp3Data.id,
                title: mp3Data.title,
                artist: mp3Data.artist,
                album: mp3Data.album
            };
        });
        //console.log(list[0].title);
        setSongTitle(list[0].title);
        setMp3ListLength(list.length);
        setMp3List(list);
    }, []);


    const onEndedHandler = () => {
        //console.log(mp3ListLength);
        if(id < mp3ListLength){
            setId(id + 1);
            setUrl("/mp3/stream/"+(id + 1));
            setSongTitle(mp3List[id].title);
        } else {
            setId(1);
            setUrl("/mp3/stream/1");
            setSongTitle(mp3List[0].title);
        }
    }

    const onClickHandler = (id, title) => {
        setId(id);
        setSongTitle(title);
        setUrl("/mp3/stream/"+ id);
    }


  return (
    <div className="App">
      <div className="App-header">
          <h2>
              MP3 스트리밍 테스트입니다.
          </h2>
          <Datatable
              mp3List={mp3List}
              onClick={onClickHandler}
          />
          <AudioPlayer
              src={url}
              onEnded={onEndedHandler}
          />
          <p>지금 노래: {id}.{songTitle}</p>
      </div>
    </div>
  );
}

export default App;
