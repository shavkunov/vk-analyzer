import $ from "jquery";
import React from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

class StartForm extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            id: "",
            posts: 0.
        };
    }

    render () {
        return (
            <h1> Hello, react </h1>
        );
    }
}

export default StartForm;