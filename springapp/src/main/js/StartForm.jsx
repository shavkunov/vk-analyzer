'use strict';

import React from 'react';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';

class StartForm extends React.Component {
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

        return (
            <div style={centerFormStyle}>

                    <TextField
                        floatingLabelText="Link"/> <br />

                    <TextField
                        floatingLabelText="Posts"/> <br />

                    <RaisedButton label="Submit"
                                  style={flexCenterStyle}/>  <br />

            </div>
        );
    }
}

export default StartForm;