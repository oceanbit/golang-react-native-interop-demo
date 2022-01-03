import React from 'react';
import {
  Button,
  NativeModules,
  SafeAreaView,
  ScrollView,
  StatusBar,
  Text,
} from 'react-native';
import {selectDirectory} from 'react-native-directory-picker';

const App = () => {
  const [msg, setMsg] = React.useState('Before the update');

  const pickDirectory = () => {
    selectDirectory()
      .then(async directory => {
        console.log('BEFORE WRITE');
        await NativeModules.HelloModule.writeFile(directory);
        console.log('AFTER WRITE');
        const newVal = await NativeModules.HelloModule.readFile(directory);
        console.log('HELLO, WORLD');
        setMsg(newVal);
      })
      .catch(err => {
        setMsg('ERROR: ' + err);
      });
  };

  return (
    <SafeAreaView>
      <StatusBar />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <Button title={'Pick Directory'} onPress={pickDirectory} />
        <Text>{msg}</Text>
      </ScrollView>
    </SafeAreaView>
  );
};

export default App;
