package com.feicui.gitdroid.repo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicui.gitdroid.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/7.
 */
public class RepoAdapter extends BaseAdapter{

    private final ArrayList<Repo> datas;

    public RepoAdapter() {
        datas=new ArrayList<Repo>();
    }
    public void addAll(Collection<Repo> repos){
        datas.addAll(repos);
        notifyDataSetChanged();
    }
    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Repo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.layout_item_repo,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder= (ViewHolder) convertView.getTag();

        Repo repo=getItem(position);
        viewHolder.tvRepoInfo.setText(repo.getDescription());
        viewHolder.tvRepoName.setText(repo.getFullName());
        viewHolder.tvRepoStars.setText(repo.getStargazersCount()+"");
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),viewHolder.ivIcon);
        return convertView;
    }

    static class ViewHolder{
        @Bind(R.id.ivIcon)ImageView ivIcon;
        @Bind(R.id.tvRepoName)TextView tvRepoName;
        @Bind(R.id.tvRepoInfo) TextView tvRepoInfo;
        @Bind(R.id.tvRepoStars) TextView tvRepoStars;
        ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
