import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      copyright="2024 Anbuz出品"
      links={[
        {
          key: 'Anbuz',
          title: 'Anbuz',
          href: 'https://space.bilibili.com/59470280',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined/> Github</>,
          href: 'https://github.com/Anbuz2019',
          blankTarget: true,
        },
        {
          key: 'CSDN',
          title: 'CSDN',
          href: 'https://blog.csdn.net/qq_63478094?spm=1000.2115.3001.5343',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
