import $ from "jquery";
import React from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

class StartForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: "",
            posts: 0
        };
    }

    render () {
        const centerFormStyle = {
            position: 'absolute',
            top: '50%',
            left: '50%',
            transform: 'translate(-50%, -50%)'
        };

        const flexCenterStyle = {
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center'
        };

        // TODO : one function
        let handleIdChange = (event) => {
            this.setState({
                id: event.target.value,
            });
        };

        let handlePostsChange = (event) => {
            this.setState({
                posts: event.target.value,
            });
        };

        let handleSubmit = () => {
            // TODO : make one file with server's name and port
            let url = "http://localhost:8080/link=" + this.state.id + "&posts=" + this.state.posts;

            console.log(url);
            $.getJSON(url, function (data) {
                console.log(data);
            });
        };

        // TODO : handle form input: errors, validation. Need to generate forms
        return (
            <div style={centerFormStyle}>

                    <TextField
                        floatingLabelText="Link"
                        onChange={handleIdChange}/> <br />

                    <TextField
                        floatingLabelText="Posts"
                        onChange={handlePostsChange}/> <br />

                    <RaisedButton label="Submit"
                                  style={flexCenterStyle}
                                  onClick={handleSubmit}/>  <br />

            </div>
        );
    }
}

export default StartForm;