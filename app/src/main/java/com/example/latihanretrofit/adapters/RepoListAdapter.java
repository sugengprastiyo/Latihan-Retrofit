package com.example.latihanretrofit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.latihanretrofit.databinding.RepolistItemBinding;
import com.example.latihanretrofit.models.Repo;
import java.util.List;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.RepoViewHolder> {

    private final List<Repo> mRepoList;

    public class RepoViewHolder extends RecyclerView.ViewHolder{
        private RepolistItemBinding binding;

        public RepoViewHolder(RepolistItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Repo repo){
            binding.setRepo(repo);
            binding.executePendingBindings();
        }
    }

    public RepoListAdapter(List<Repo> mRepoList) {
        this.mRepoList = mRepoList;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RepolistItemBinding itemBinding = RepolistItemBinding.inflate(layoutInflater, parent, false);
        return new RepoViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        Repo mRepo = mRepoList.get(position);
        holder.bind(mRepo);
    }

    @Override
    public int getItemCount() {
        return mRepoList.size();
    }

}
