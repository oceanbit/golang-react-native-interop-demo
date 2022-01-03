import React from 'react';
import {
  Button,
  NativeEventEmitter,
  NativeModules,
  SafeAreaView,
  ScrollView,
  StatusBar,
  Text,
} from 'react-native';
import {selectDirectory} from 'react-native-directory-picker';
import {useGetAndroidPermissions} from './use-get-android-permissions';

const App = () => {
  const [msg, setMsg] = React.useState('Before the update');
  const [directory, setDirectory] = React.useState('');
  const [bytes, setBytes] = React.useState([0]);

  useGetAndroidPermissions();

  React.useEffect(() => {
    if (!directory) {
      return;
    }

    NativeModules.HelloModule.observeFile(directory)
      .then(() => {
        setMsg('Ready to read file');
      })
      .catch(err => setMsg('ERROR: ' + err));

    const eventEmitter = new NativeEventEmitter(NativeModules.HelloModule);

    const eventListener = eventEmitter.addListener(
      'ReadChange',
      (event: {
        bytes: number[],
        currentFileChunkIndex: number,
        fileSize: number,
      }) => {
        setBytes(event.bytes);
      },
    );

    return () => {
      eventListener.remove();
      NativeModules.HelloModule.stopObservingFile();
    };
  }, [directory]);

  const pickDirectory = () => {
    selectDirectory()
      .then(async directory => {
        setDirectory(directory);
        setMsg('Ready get directory data file');
      })
      .catch(err => {
        setMsg('ERROR: ' + err);
      });
  };

  const goUp = () => {
    NativeModules.HelloModule.goUp();
  };

  const goDown = () => {
    NativeModules.HelloModule.goDown();
  };

  return (
    <SafeAreaView>
      <StatusBar />
      <ScrollView contentInsetAdjustmentBehavior="automatic">
        <Button title={'Pick Directory'} onPress={pickDirectory} />
        <Text>{msg}</Text>
        <Button title={'Go up'} onPress={goUp} />
        <Button title={'Go down'} onPress={goDown} />
        <ScrollView>
          {bytes.map((byte, index) => (
            <Text key={index}>{byte}</Text>
          ))}
        </ScrollView>
      </ScrollView>
    </SafeAreaView>
  );
};

export default App;
