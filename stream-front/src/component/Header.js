import React, {useCallback, useState} from 'react';
import './Header.css'

const Header = () => {
    const [success, setSuccess] = useState(false);

    const buttonClickHandler = useCallback(async () => {
        const response = await fetch("/mp3/scan");
        console.log(response);
        if (!response.ok){
            throw new Error('뭔가 잘못된 거 같아');
        }

        if (response.status === 200){
            setSuccess(true);
            window.location.reload();
        }

        }, []);

    return (
        <div className="Header">
            <h2 className="H2">MP3 Streaming Espresso</h2>
            <button className="Button" onClick={buttonClickHandler}>Mp3 Scan</button>
        </div>
    )
}

export default Header;