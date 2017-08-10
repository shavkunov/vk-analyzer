'use strict';

import React from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

class StartForm extends React.Component {
    render () {
        return (
            <div style={{display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center'}}>
                <div>
                    <TextField
                        floatingLabelText="Link"/>

                    <TextField
                        floatingLabelText="Posts"/>

                    <RaisedButton label="Submit"/>
                </div>
            </div>
        );
    }
}

export default StartForm;