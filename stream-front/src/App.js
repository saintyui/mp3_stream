
import logo from './logo.svg';
import './App.css';
import {useEffect, useState} from "react";
import Datatable from "./component/Datatable";
import AudioPlayer from "react-h5-audio-player";
import Header from "./component/Header";
import 'react-h5-audio-player/lib/styles.css';
import playButton from "react-mp3-player/dist/icons/PlayButton";


function App() {
    const [mp3List, setMp3List] = useState([]);
    const [mp3ListLength, setMp3ListLength] = useState(0);
    const [idx, setIdx] = useState('1');
    const [url, setUrl] = useState('');
    const [songTitle, setSongTitle] = useState('');

    const setScreenSize = () => {
        let vh = window.innerHeight * 0.01;
        document.documentElement.style.setProperty("--vh", `${vh}px`);
    }

    useEffect(() => {
        setScreenSize();
    }, []);

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
        if(idx < mp3ListLength){
            setIdx(idx + 1);
            setUrl("/mp3/stream/"+(idx + 1));
            setSongTitle(mp3List[idx].title);
        } else {
            setIdx(1);
            setUrl("/mp3/stream/1");
            setSongTitle(mp3List[0].title);
        }
    }

    const onClickHandler = (id, title) => {
        setIdx(id);
        setSongTitle(title);
        setUrl("/mp3/stream/"+ id);
    }

    const onClickPlayHandler = () => {
        //console.log(idx);
        if(url === ''){
            setIdx(1);
            setUrl("/mp3/stream/"+1);
            setSongTitle(mp3List[0].title);
        }
    }

  return (
      <div className="Contents">
          <div className="Container">
             <Header />
              <Datatable
                  mp3List={mp3List}
                  onClick={onClickHandler}
              />
              <p>지금 노래: {idx}.{songTitle}</p>
          </div>
          <div className="Footer">
              <AudioPlayer
                  src={url}
                  onEnded={onEndedHandler}
                  onPlayError={onClickPlayHandler}
              />
          </div>
      </div>
  );
}

export default App;
